f = open("input.txt", "w")
n=input()
n=int(n)
#print(n.type)
f.write(str(n))
f.write(' ')
f.write( str(int((n*(n+1))/2))  )
f.write('\n')
        

for i in range(1,n+1):
    for j in range(i+1,n+1):
        f.write(str(i))
        f.write(' ')
        f.write(str(j))
        f.write('\n')
        

f.close()

