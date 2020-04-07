from django.db import models

class filesModel(models.Model):
    qr = models.CharField(max_length=400, primary_key=True)
    username = models.CharField(max_length=400, default='', blank=True)
    status = models.CharField(max_length=400, default='', blank=True)
    path = models.CharField(max_length=1000, default='', blank=True)
    Office = models.CharField(max_length=400, default='')

