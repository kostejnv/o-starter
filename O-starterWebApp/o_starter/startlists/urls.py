from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('<int:race_id>/', views.view_all, name='view_all'),
    path('<int:race_id>/changes', views.view_changes, name='view_changes'),
    path('<int:race_id>/unstarted', views.view_unstarted, name='view_unstarted'),
]
