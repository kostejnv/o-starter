from django.db import models
import random, string


def get_unique_key() -> str:
    """
    return unique string id

    Returns unique string id from numeric-alphabetic characters of length 6
    """

    key = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(6))
    all_race_ids = list(map(lambda race: race.id, Race.objects.all()))
    while key in all_race_ids:
        key = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(6))
    return key


class Race(models.Model):
    """
    Database table of races
    """

    id = models.CharField(max_length=6, primary_key=True, default=get_unique_key)
    name = models.CharField(max_length=200)

    def __str__(self) -> str:
        return self.name


class Change(models.Model):
    """
    Database table of changed runners
    """

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

    def old_record(self) -> str:
        """
        return data of runner before changes
        """
        return f'{self.old_reg_number} {self.old_lastname} {self.old_firstname}     {self.old_sinumber}'

    def new_record(self) -> str:
        """
        return data of runner after changes
        """
        return f'{self.new_reg_number} {self.new_lastname} {self.new_firstname}     {self.new_sinumber}'

    def __str__(self) -> str:
        return f'{self.old_firstname} {self.old_lastname} -> {self.new_firstname} {self.new_lastname}'


class Unstarted_runner(models.Model):
    """
    Database table of unstarted runners
    """

    race = models.ForeignKey(Race, on_delete=models.CASCADE)

    firstname = models.CharField(max_length=200, default='')
    lastname = models.CharField(max_length=200, default='')
    start_time = models.IntegerField(default=0)
    club = models.CharField(max_length=200, default='XXX')
    sinumber = models.IntegerField(default=0)
    start_number = models.IntegerField(default=0)
    reg_number = models.CharField(max_length=10, default='XXX0000')  # more beacause of undefined behavior
    category = models.CharField(max_length=200, default='')

    def __str__(self) -> str:
        return f'{self.reg_number} {self.lastname} {self.firstname}'
