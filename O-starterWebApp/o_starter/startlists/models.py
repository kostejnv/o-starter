from django.db import models


class Race(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200)

    def __str__(self):
        return self.name


class Change(models.Model):
    race = models.ForeignKey(Race, on_delete=models.CASCADE)
    old_start_time = models.CharField(max_length=20, default='00:00')
    old_reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    old_firstname = models.CharField(max_length=50, default='')
    old_lastname = models.CharField(max_length=50, default='')
    old_sinumber = models.IntegerField(default=0)

    new_start_time = models.CharField(max_length=20, default='00:00')
    new_reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    new_firstname = models.CharField(max_length=200, default='')
    new_lastname = models.CharField(max_length=200, default='')
    new_sinumber = models.IntegerField(default=0)

    def old_record(self):
        return f'{self.old_reg_number} {self.old_lastname} {self.old_firstname}     {self.old_sinumber}'

    def new_record(self):
        return f'{self.new_reg_number} {self.new_lastname} {self.new_firstname}     {self.new_sinumber}'

    def __str__(self):
        return f'{self.old_firstname} {self.old_lastname} -> {self.new_firstname} {self.new_lastname}'


class Unstarted_runner(models.Model):
    race = models.ForeignKey(Race, on_delete=models.CASCADE)
    start_time = models.IntegerField(default=0)
    reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    firstname = models.CharField(max_length=50, default='')
    lastname = models.CharField(max_length=50, default='')

    def __str__(self):
        return f'{self.reg_number} {self.lastname} {self.firstname}'
