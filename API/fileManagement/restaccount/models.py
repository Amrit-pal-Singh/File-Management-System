from django.db import models
from django.contrib.auth.models import AbstractUser
# Create your models here.

class DriverUser(AbstractUser):
	name = models.CharField(max_length=250, default='Name')
	hierarchyName = models.CharField("Hierarchy_Name", max_length=250, default='none')
	hierarchyLevel = models.CharField("Hierarchy_Level", max_length=250, default='none')

	def __str__(self):
		return self.username