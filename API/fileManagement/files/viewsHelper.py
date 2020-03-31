from .models import filesModel
from hierarchy.models import hierarchyModel
from hierarchy.models import officeName

def set_status(name, status):
    office = officeName.objects.filter(name=name)
    id = office.id_category
    levels = get_levels(id)
    setLevel = ''
    for i in range(len(levels)):
        if(levels[i] == status):
            setLevel = levels[i+1]
            break
    if(setLevel == ''):
        setLevel = levels[0]
    return setLevel

def set_path(name, path):
    levels = path.split('-')
    curStatus = set_status(name, levels[-1])


def get_levels(id):
    hiers = {}
    allLevels = hierarchyModel.objects.all()
    for level in allLevels:
        hiers[office] = level.split('-')
    return hiers[id]
