from . import models

def create_change(change_str, race):
    parameters = change_str.split("@#")
    race.change_set.create(
        old_start_time=parameters[0],
        old_reg_number=parameters[1],
        old_firstname=parameters[2],
        old_lastname=parameters[3],
        old_sinumber=parameters[4],
        new_start_time=parameters[5],
        new_reg_number=parameters[6],
        new_firstname=parameters[7],
        new_lastname=parameters[8],
        new_sinumber=parameters[9])

def add_all_changes(all_changes_str, race_id):
    race = models.Race.objects.get(pk=race_id)
    changes_str = all_changes_str.split("$&")
    for change_str in changes_str:
        create_change(change_str, race)


def create_unstarted_runner(unstarted_runner_str, race_id):
    parameters = unstarted_runner_str.split("@#")
    race = models.Race.objects.get(pk=race_id)
    race.unstarted_runner_set.create(
        start_time=parameters[0],
        reg_number=parameters[1],
        firstname=parameters[2],
        lastname=parameters[3])

def add_all_unstarted_runners(all_unstarted_runners_str, race_id):
    race = models.Race.objects.get(pk=race_id)
    unstarted_runners_str = all_unstarted_runners_str.split("$&")
    for unstarted_runner_str in unstarted_runners_str:
        create_change(unstarted_runner_str, race)
