from django.db import models


class Office(models.Model):
	id_category = models.IntegerField(default=0)
	name = models.CharField(max_length=400, default=0)

	def __str__(self):
		return self.name

# id is inbuilt and get incremented itself
class hierarchyModel(models.Model):
	timestamp = models.DateTimeField(auto_now=False, auto_now_add = True)
	office = models.CharField(max_length=400, default=0)

	officeId = models.ForeignKey(Office, on_delete=models.CASCADE)
	# officeId = models.IntegerField(primary_key=True) 
	
	levels = models.CharField(max_length=400, default=0)
	def __str__(self):
		return str(self.office)

