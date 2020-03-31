from django.db import models


# id is inbuilt and get incremented itself
class hierarchyModel(models.Model):
	timestamp = models.DateTimeField(auto_now=False, auto_now_add = True)
	officeName = models.CharField(max_length=400, default=0)
	officeId = models.IntegerField(primary_key=True)
	levels = models.CharField(max_length=400, default=0)
	def __str__(self):
		return str(self.officeName)


class officeName(models.Model):
	id_category = models.IntegerField(default=0)
	name = models.CharField(max_length=400, default=0)

	def __str__(self):
		return self.name