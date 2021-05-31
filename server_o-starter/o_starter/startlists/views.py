import json
from types import SimpleNamespace

from django.shortcuts import get_object_or_404, render
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse, HttpRequest
from django.views.decorators.csrf import csrf_exempt

from . import models
from django.urls import reverse
from .variables import server_key
from .model_data_preprocessing import *

# Create your views here.
from .models import Race, Unstarted_runner


def index(request):
    """return empty html

    """

    return HttpResponse(status=201)


@csrf_exempt
def create_race(request):
    """create new race in server database

        Parameters
        ----------
        request : HttpRequest
            request for create new race in server database.

        Returns
        -------
        JsonResponse
            id of race  else 404
    """

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
def get_data(request, race_id):
    """stored data from request to server database

        If unstarted runners already exists in database, they are deleted and stored new ones

        Parameters
        ----------
        request : HttpRequest
            request for storing data to server database.
            It contains JSON body with unstarted runners and changes

        race_id : str
            id of race in server database

        Returns
        -------
        HttpResponse
            200 if the data was stored else 404
    """

    if request.method == 'POST' and request.accepts("application/json"):
        post_data = json.loads(request.body, object_hook=lambda d: SimpleNamespace(**d))
        try:
            if (post_data.server_key != server_key):
                raise Exception("Wrong access")

            race = Race.objects.get(id=race_id)

            for change in post_data.changed_runners:
                insert_change_to_database(change, race)

            if len(post_data.unstarted_runners) != 0:
                race.unstarted_runner_set.all().delete()

            for unstarted_runner in post_data.unstarted_runners:
                insert_unstarted_runner_to_database(unstarted_runner, race)

            return HttpResponse(status=200)

        except Exception as e:
            return HttpResponse(status=404)
    return HttpResponse(status=404)


def view_changes(request, race_id):
    """return HTML response with changed runners in body

        Parameters
        ----------
        request : HttpRequest
            request for returning changed runners

        race_id : str
            id of race in server database

        Returns
        -------
        HttpResponse
            HTML response with table of changed runners of given race in body
            or 404 error if invalid race_id
    """

    race = get_object_or_404(models.Race, pk=race_id)
    context = {'race': race}
    return render(request, 'startlists/view_changes.html', context)
    pass


def view_unstarted(request, race_id):
    """return HTML response with unstarted runners in body

        Parameters
        ----------
        request : HttpRequest
            request for returning unstarted runners

        race_id : str
            id of race in server database

        Returns
        -------
        HttpResponse
            HTML response with table of unstarted runners of given race in body
            or 404 error if invalid race_id
        """

    race = get_object_or_404(models.Race, pk=race_id)
    context = {'race': race}
    return render(request, 'startlists/view_unstarted.html', context)
    pass
