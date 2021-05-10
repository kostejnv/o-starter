from django.contrib import admin
from django.urls import include, path

urlpatterns = [
    path('startlists/', include('startlists.urls')),
    path('admin/', admin.site.urls),
    path('', include('startlists.urls'))
]

