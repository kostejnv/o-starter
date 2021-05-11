from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('<int:race_id>/', views.view_all, name='view_all'),
    path('<int:race_id>/changes', views.view_changes, name='view_changes'),
    path('<int:race_id>/unstarted', views.view_unstarted, name='view_unstarted'),
    path('create_race', views.create_race, name='view_create_race'),
    path('<int:race_id>/send_data', views.send_data, name='view_send_data')
]
