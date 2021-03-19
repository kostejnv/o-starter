from django.db import models

class Race(models.Model):
    name = models.CharField(max_length=200)
    key = models.IntegerField()
    update_time = models.DateField  ()

class Change(models.Model):
    race = models.ForeignKey(Race, on_delete=models.CASCADE)
    start_time = models.IntegerField(default=0)
    old_reg_number = models.CharField(max_length=10, default='XXX0000') # more beacause of undefined behavior
    old_firstname = models.CharField(max_length=50, default='')
    old_lastname = models.CharField(max_length=50, default='')
    old_sinumber = models.IntegerField()
    new_reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    new_firstname = models.CharField(max_length=50, default='')
    new_lastname = models.CharField(max_length=50, default='')
    new_sinumber = models.IntegerField(default=0)

class Unstarted_runner(models.Model):
    race = models.ForeignKey(Race, on_delete=models.CASCADE)
    start_time = models.IntegerField(default=0)
    reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    firstname = models.CharField(max_length=50, default='')
    lastname = models.CharField(max_length=50, default='')






