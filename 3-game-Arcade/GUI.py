from Tkinter import *
import subprocess
master = Tk()


def hangman():
    subprocess.call(['python.exe', 'hangman.py'])
    p=subprocess.Popen('hangman.py')
    p.communicate()

def diceroll():
    subprocess.call(['python.exe', 'diceroll.py'])
    p=subprocess.Popen('diceroll.py')
    p.communicate()
def montyhall():
    subprocess.call(['python.exe', 'makeadeal.py'])
    p=subprocess.Popen('makeadeal.py')
    p.communicate()
text = Text()
text.insert(INSERT, "                                   Game Board") 

text.pack()
a = Button(master, text="Hangman", command=hangman)
a.pack()
#a.place( height=100, width=100)
b = Button(master, text="diceroll", command=diceroll)
b.pack()
text.pack()
c = Button(master, text="Makeadeal", command=montyhall)
c.pack()
#b.place( height=100, width=100)
#text.place( height=50, width=100)



mainloop()
