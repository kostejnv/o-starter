from . import models

def insert_change_to_database(change, race):
    race.change_set.create(
        old_firstname= change.old_given,
        old_lastname = change.old_family,
        old_start_time = change.old_start_time,
        old_club = change.old_club_short,
        old_sinumber = change.old_card_number,
        old_start_number = change.old_start_number,
        old_reg_number = change.old_registration_id,
        old_category = change.old_category,

        new_firstname = change.new_given,
        new_lastname = change.new_family,
        new_start_time = change.new_start_time,
        new_club = change.new_club_short,
        new_sinumber = change.new_card_number,
        new_start_number = change.new_start_number,
        new_reg_number = change.new_registration_id,
        new_category = change.new_category)
    race.save()


def insert_unstarted_runner_to_database(unstarted_runner, race):
    race.unstarted_runner_set.create(
        firstname=unstarted_runner.old_given,
        lastname=unstarted_runner.old_family,
        start_time=unstarted_runner.old_start_time,
        club=unstarted_runner.old_club_short,
        sinumber=unstarted_runner.old_card_number,
        start_number=unstarted_runner.old_start_number,
        reg_number=unstarted_runner.old_registration_id,
        category=unstarted_runner.old_category)
    race.save()


