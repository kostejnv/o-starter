from django.shortcuts import get_object_or_404, render
from django.http import HttpResponse, HttpResponseRedirect
from . import models
from django.urls import reverse

# Create your views here.
def index(request):
    return HttpResponse(status=201)

def create_race(request):
    pass

def add_changes(request):
    pass

def add_unstarted(request):
    pass

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