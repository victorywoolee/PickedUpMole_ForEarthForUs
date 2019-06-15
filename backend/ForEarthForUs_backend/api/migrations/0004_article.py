# Generated by Django 2.2.2 on 2019-06-15 07:23

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0003_campaign'),
    ]

    operations = [
        migrations.CreateModel(
            name='Article',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=50)),
                ('subTitle', models.CharField(max_length=100)),
                ('image', models.FileField(upload_to='images/article/')),
                ('link', models.URLField(max_length=256)),
            ],
            options={
                'verbose_name': 'article',
                'verbose_name_plural': 'articles',
                'db_table': 'articles',
            },
        ),
    ]
