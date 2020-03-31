import datetime

def saveLog(text):
    file = open('log.txt', 'a')
    text = str(datetime.datetime.now()) + '  ' + str(text)
    file.write(text)
    file.write('\n')

