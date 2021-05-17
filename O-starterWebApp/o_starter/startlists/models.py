from django.db import models


class Race(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200)

    def __str__(self):
        return self.name


class Change(models.Model):
    race = models.ForeignKey(Race, on_delete=models.CASCADE)

    old_firstname = models.CharField(max_length=200, default='')
    old_lastname = models.CharField(max_length=200, default='')
    old_start_time = models.BigIntegerField(default=0)
    old_club = models.CharField(max_length=200, default='XXX')
    old_sinumber = models.IntegerField(default=0)
    old_start_number = models.IntegerField(default=0)
    old_reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    old_category = models.CharField(max_length=200, default='')

    new_firstname = models.CharField(max_length=200, default='')
    new_lastname = models.CharField(max_length=200, default='')
    new_start_time = models.BigIntegerField(default=0)
    new_club = models.CharField(max_length=200, default='XXX')
    new_sinumber = models.IntegerField(default=0)
    new_start_number = models.IntegerField(default=0)
    new_reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    new_category = models.CharField(max_length=200, default='')

    def old_record(self):
        return f'{self.old_reg_number} {self.old_lastname} {self.old_firstname}     {self.old_sinumber}'

    def new_record(self):
        return f'{self.new_reg_number} {self.new_lastname} {self.new_firstname}     {self.new_sinumber}'

    def __str__(self):
        return f'{self.old_firstname} {self.old_lastname} -> {self.new_firstname} {self.new_lastname}'


class Unstarted_runner(models.Model):
    race = models.ForeignKey(Race, on_delete=models.CASCADE)

    firstname = models.CharField(max_length=200, default='')
    lastname = models.CharField(max_length=200, default='')
    start_time = models.IntegerField(default=0)
    club = models.CharField(max_length=200, default='XXX')
    sinumber = models.IntegerField(default=0)
    start_number = models.IntegerField(default=0)
    reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    category = models.CharField(max_length=200, default='')

    def __str__(self):
        return f'{self.reg_number} {self.lastname} {self.firstname}'
