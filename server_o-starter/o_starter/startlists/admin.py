from django.contrib import admin

from .models import Race, Change, Unstarted_runner

# Register your models here.
admin.site.register(Race)
admin.site.register(Change)
admin.site.register(Unstarted_runner)

