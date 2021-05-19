import json
from types import SimpleNamespace

from django.shortcuts import get_object_or_404, render
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.views.decorators.csrf import csrf_exempt

from . import models
from django.urls import reverse
from .variables import server_key
from .model_data_preprocessing import *

# Create your views here.
from .models import Race


def index(request):
    return HttpResponse(status=201)


@csrf_exempt
def create_race(request):
    if request.method == 'POST' and request.accepts("application/json"):
        post_data = json.loads(request.body, object_hook=lambda d: SimpleNamespace(**d))
        try:
            if (post_data.server_key != server_key):
                raise Exception("Wrong access")

            race = Race(name=post_data.name)
            race.save()

            output = {}
            output['id'] = race.id
            return JsonResponse(output)
        except:
            return HttpResponse(status=404)
    return HttpResponse(status=404)


@csrf_exempt
def send_data(request, race_id):
    if request.method == 'POST' and request.accepts("application/json"):
        post_data = json.loads(request.body, object_hook=lambda d: SimpleNamespace(**d))
        try:
            if (post_data.server_key != server_key):
                raise Exception("Wrong access")

            race = Race.objects.get(id=race_id)

            for change in post_data.changed_runners:
                insert_change_to_database(change, race)

            for unstarted_runner in post_data.unstarted_runners:
                insert_unstarted_runner_to_database(unstarted_runner, race)

            return HttpResponse(status=200)

        except Exception as e:
            return HttpResponse(status=404)
    return HttpResponse(status=404)


def view_all(request, race_id):
    return HttpResponseRedirect(reverse('view_changes', args=[race_id]))


def view_changes(request, race_id):
    race = get_object_or_404(models.Race, pk=race_id)
    context = {'race': race}
    return render(request, 'startlists/view_changes.html', context)
    pass


def view_unstarted(request, race_id):
    race = get_object_or_404(models.Race, pk=race_id)
    context = {'race': race}
    return render(request, 'startlists/view_unstarted.html', context)
    pass
