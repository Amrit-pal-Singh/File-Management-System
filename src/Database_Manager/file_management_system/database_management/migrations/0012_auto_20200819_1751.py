# Generated by Django 3.0.8 on 2020-08-19 17:51

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('database_management', '0011_auto_20200819_1750'),
    ]

    operations = [
        migrations.AlterField(
            model_name='file',
            name='plan_to_send_time',
            field=models.DateTimeField(blank=True, null=True),
        ),
    ]
