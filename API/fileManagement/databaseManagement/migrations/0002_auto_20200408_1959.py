# Generated by Django 3.0.5 on 2020-04-08 19:59

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('databaseManagement', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='hierarchy',
            old_name='level',
            new_name='levels',
        ),
    ]