from django.db import models
from django.contrib.auth.models import User
from phonenumber_field.modelfields import PhoneNumberField


class UserAccount(models.Model):
    '''
    All users will have this account, Only users who have this account
    are allowed to generate file.
    '''
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    phone_number = PhoneNumberField()
    address = models.CharField(max_length=200)

    def __str__(self):
        return str(self.user.first_name)+" "+str(self.user.last_name)


class Office(models.Model):
    '''
    Office in which the app will be installed.
    The office can have multiple hierarchies.
    '''
    name = models.CharField(max_length=100)
    office_id = models.CharField(max_length=100, unique=True)
    # hierarchies = models.ManyToManyField(Hierarchy, blank=True)

    def __str__(self):
        return str(self.name) + "-" + str(self.office_id)


class Position(models.Model):
    '''
    Store the position in the office.
    '''
    name = models.CharField(max_length=100)
    office = models.ForeignKey(Office, on_delete=models.CASCADE)

    def __str__(self):
        return str(self.name) + " " + str(self.office.name) + "-" + str(self.office.office_id)

class Hierarchy(models.Model):
    '''
    This is a basic flow which the file must follow.
    '''
    name = models.CharField(max_length=100)
    hierarchy_id = models.CharField(max_length=100, unique=True)
    # level = # list of positions
    # all levels must be in lowercase
    levels = models.CharField(max_length=1000)

    def __str__(self):
        return str(self.name)+" "+str(self.hierarchy_id)


class OfficerAccount(models.Model):
    '''
    If the user is a officer then this model will be called
    Otherwise simple auth.models.User will be saved for a customer.
    '''
    user = models.OneToOneField(UserAccount, on_delete=models.CASCADE)
    position = models.ForeignKey(Position, on_delete=models.CASCADE)
    office = models.ForeignKey(Office, on_delete=models.CASCADE)

    def __str__(self):
        return str(self.user.user.first_name)+" "+str(self.user.user.last_name)+" "+str(self.position)+" "+str(self.office)


class Files(models.Model):
    '''
    All the files in completed or continuing process will be saved in this. 
    currentLevel will be set using the hierarchy.
    The path will store all the users which scanned the app.
    '''
    qr = models.CharField(max_length=1000, unique=True)
    user = models.OneToOneField(UserAccount, on_delete = models.CASCADE)
    office = models.ForeignKey(Office, on_delete=models.CASCADE)
    # current_level = models.ForeignKey(Position, on_delete=models.CASCADE)
    # hierarchy = models.ForeignKey(Hierarchy, on_delete=models.CASCADE)
    path = models.CharField(max_length=1000, default='User')
    time_created = models.DateTimeField(auto_now_add=True)
    last_update_time = models.DateTimeField(auto_now=True) # this work only with save() not update()

    def __str__(self):
        return str(self.qr)