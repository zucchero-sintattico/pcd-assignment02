import sys
import random
import os
possible_strings = [" class", " var"," pippo", " pluto", " paperino" , " \n", " \t", " ", ""]

def generate_file(files):
    counter=0
    try:
        os.mkdir(sys.path[0] + "/generated_files")
    except Exception as e:
        print(e)

    for i in range(0, int(files)):
        try:
            path = sys.path[0] + "/generated_files/src_" + str(i) + "/"
            filename = "file" + str(i) + ".java"
            content = "//Auto-generated-file\n"
            if random.randint(0, 100) <= 30:
                path = path +"nested/"

            print("Creating file: " + path + filename)
            os.makedirs(path)

            for z in range(0, random.randint(0, 1000)):
                append = random.choice(possible_strings)
                if append == " \n": counter = counter + 1
                content = content + append
            with open(path + filename, "xb") as f:
                f.write(content.encode("utf-8"))
        except Exception as e:
            print(e)

    total_lines = counter + int(files)
    print("Counter: " + str(total_lines))
    with open(sys.path[0] + "/counter.txt", "wb") as f:
        f.write(str(total_lines).encode("utf-8"))

if __name__ == "__main__":
    n_files = sys.argv[1]
    generate_file(n_files)
