import os
import sys
from pathlib import Path
import re

if len(sys.argv) != 2:
    print("Please provide exactly one parameter!")
    exit()

if not sys.argv[1].endswith(".js") or not os.path.isfile(sys.argv[1]):
    print("Must provide a valid JavaScript file!")

loadedModules = []

def patchImports(fileName):
    fileModuleName = re.sub(r".js$", "", fileName)
    if fileModuleName in loadedModules:
        return True
    if not os.path.isfile(fileName):
        return False
    loadedModules.append(fileModuleName)
    with open(fileName, "r") as readableFile:
        fileContent = readableFile.read()
        fileContent = fileContent.rstrip()

        for moduleImport in re.findall(r"import {.+} from [\"'`].+[\"'`];", fileContent):
            importPath = re.findall(r"[\"'`].+[\"'`]", moduleImport)[0]
            importPath = re.sub(r"[\"'`]", "", importPath)
            if not importPath.endswith(".js"):
                importPath += ".js"
            if importPath.startswith("./"):
                importPath = importPath[2:]
            
            patchedContent = patchImports(importPath)
            if patchedContent == False:
                print("There has been an error patching " + importPath)
                continue
            if patchedContent == True:
                #print("Module " + importPath + " already imported")
                fileContent = fileContent.replace(moduleImport, "")
                continue
            patchedContent = patchedContent.replace("export ", "")
            fileContent = fileContent.replace(moduleImport, "/* CONCATENATED MODULE FROM " +importPath+" */\n" + patchedContent + "\n")

        return fileContent

baseModuleName = re.sub(r".js$", "", sys.argv[1])
patchedModule = patchImports(sys.argv[1])
if not isinstance(patchedModule, str):
    print("Error concatenating modules: " + patchedModule)
    exit()

with open(baseModuleName + "b.js", "w") as outFile:
    outFile.write(patchedModule)