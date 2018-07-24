
from random import randint

number = input("Enter file number: ")
f = open(number + ".in", "w")

darray = []
arraylen = input("Enter row/column length: ")


for i in range(0, int(arraylen)):
	row = []
	for j in range(0, int(arraylen)):
		if i ==j:
			row.append(randint(0, 99))
		else:
			row.append(randint(0, 1))
	darray.append(row)

f.write(arraylen + "\n")
for row in darray:
	f.write(" ".join(str(x) for x in row) + "\n")
