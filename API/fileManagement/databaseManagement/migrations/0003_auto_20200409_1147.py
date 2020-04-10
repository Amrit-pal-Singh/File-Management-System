# Generated by Django 3.0.5 on 2020-04-09 11:47

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('databaseManagement', '0002_auto_20200408_1959'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='files',
            name='current_level',
        ),
        migrations.RemoveField(
            model_name='files',
            name='hierarchy',
        ),
        migrations.RemoveField(
            model_name='office',
            name='hierarchies',
        ),
        migrations.CreateModel(
            name='Position',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=100)),
                ('office', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='databaseManagement.Office')),
            ],
        ),
        migrations.AlterField(
            model_name='officeraccount',
            name='position',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='databaseManagement.Position'),
        ),
    ]