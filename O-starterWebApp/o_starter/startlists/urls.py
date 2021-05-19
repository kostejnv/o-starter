from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('<str:race_id>/', views.view_all, name='view_all'),
    path('<str:race_id>/changes', views.view_changes, name='view_changes'),
    path('<str:race_id>/unstarted', views.view_unstarted, name='view_unstarted'),
    path('create_race', views.create_race, name='view_create_race'),
    path('<str:race_id>/send_data', views.send_data, name='view_send_data')
]
