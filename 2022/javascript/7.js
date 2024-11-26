function calculate() {
    let input = document.getElementById("input").value;
    let commands = input.split("$ ");
    truncateArray(commands);
    commands = commands.map(command => truncateArray(command.split("\n")));
    let directory = "/";
    let root = new Directory("/", []);
    commands.forEach(element => {
        switch (true) {
            case element[0].startsWith("cd"):
                let newDirectory = element[0].split(" ")[1];
                if (newDirectory == "..") {
                    directory = directory.split("/").slice(0, -2).join("/") + "/";
                    if (directory == "") directory = "/";
                } else if (newDirectory == "/") {
                    directory = "/";
                } else {
                    directory += newDirectory + "/";
                }
                break;
            case element[0] == "ls":
                element.slice(1).forEach(fileOrDirectory => {
                    switch (fileOrDirectory.split(" ")[0]) {
                        case "dir":
                            root.addElementAt(new Directory(fileOrDirectory.split(" ")[1], []), directory);
                            break;
                        default:
                            root.addElementAt(new File(fileOrDirectory.split(" ")[1], parseInt(fileOrDirectory.split(" ")[0])), directory);
                            break;
                    }
                });
                break;
        }
    });
    document.getElementById("output").value = "dirs <= 100k: " + root.sizeOfDirsSmallerThan(100000);

    let totalSpaceAvailable = 70000000;
    let currentSpaceAvailable = Math.max(totalSpaceAvailable - root.size, 0);
    let spaceNeeded = 30000000 - currentSpaceAvailable;
    document.getElementById("output").value += "\nbiggest delDir: " + root.sizeOfSmallestDirBiggerThan(spaceNeeded);
}

class Directory {
    constructor(name, content) {
        this.name = name;
        this.content = content;
    }

    get size() {
        return this.content.reduce((accumulator, currentValue) => accumulator + currentValue.size, 0);
    }

    sizeOfDirsSmallerThan(num) {
        return this.content.reduce((accumulator, currentValue) => {
            if (!(currentValue instanceof Directory)) return accumulator;
            if (currentValue.size <= num) accumulator += currentValue.size;
            accumulator += currentValue.sizeOfDirsSmallerThan(num);
            return accumulator;
        }, 0);
    }

    sizeOfSmallestDirBiggerThan(num) {
        return this.content.reduce((accumulator, currentValue) => {
            if (!(currentValue instanceof Directory)) return accumulator;
            if (currentValue.size >= num) accumulator = Math.min(accumulator, currentValue.size);
            accumulator = Math.min(accumulator, currentValue.sizeOfSmallestDirBiggerThan(num));
            return accumulator;
        }, Infinity);
    }

    addElement(file) {
        this.content.forEach(element => {
            if (element.name == file.name && typeof file == typeof element) throw new Error("File " + file.name + " already exists");
        });
        this.content.push(file);
    }

    addElementAt(file, location) {
        let path = truncateArray(location.split("/"));
        let targetDir = this;
        for (let i = 0; i < path.length; i++) {
            targetDir = targetDir.getDirectory(path[i]);
            if (targetDir == undefined) {
                throw new Error("Directory " + path[i] + " does not exist",);
            }
        }
        targetDir.addElement(file);
    }

    getDirectory(name) {
        return this.content.find(element => element.name == name && element instanceof Directory);
    }
}

class File {
    constructor(name, size) {
        this.name = name;
        this.size = size;
    }
}

function truncateArray(arr) {
    if (arr[0] == "") arr.shift();
    if (arr[arr.length - 1] == "") arr.pop();
    return arr;
}