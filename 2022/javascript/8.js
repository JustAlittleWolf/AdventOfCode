function calculate() {
    let input = document.getElementById("input").value;
    let forest = new Forest(input);
    document.getElementById("output").value = "Visible Trees: " + forest.getAllTrees().reduce((accumulator, tree) => accumulator += tree.isVisible ? 1 : 0, 0);
    document.getElementById("output").value += "\nMax Scenic Score: " + forest.getAllTrees().reduce((accumulator, tree) => { return Math.max(accumulator, tree.scenicScore) }, 0);
}

class Tree {
    constructor(x, y, height, forest) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.forest = forest;
    }

    get isVisible() {
        return this.forest.isTreeVisible(this.x, this.y);
    }

    get scenicScore() {
        return this.forest.getScenicScore(this.x, this.y)
    }

    getNextTreeX(num) {
        return this.forest.getTree(this.x + num, this.y);
    }

    getNextTreeY(num) {
        return this.forest.getTree(this.x, this.y + num);
    }
}

class Forest {
    trees = [];
    constructor(trees) {
        trees = truncateArray(trees.split("\n"));
        trees.forEach((row, y) => {
            row = truncateArray(row.split(""));
            row.forEach((tree, x) => {
                this.addTree(new Tree(x, y, tree, this));
            });
        });
    }

    addTree(tree) {
        if (this.trees[tree.x] == undefined) this.trees[tree.x] = [];
        this.trees[tree.x][tree.y] = tree;
    }

    getTree(x, y) {
        if (this.trees[x] == undefined) return undefined;
        return this.trees[x][y];
    }

    isTreeVisible(x, y) {
        let tree = this.getTree(x, y);
        if (tree == undefined) return false;
        if (this.isTreeBorder(x, y)) return true;
        for (let i = 1; this.getTree(x + i, y).height < tree.height; i++) {
            if (this.getTree(x + i + 1, y) == undefined) return true;
        }
        for (let i = 1; this.getTree(x - i, y).height < tree.height; i++) {
            if (this.getTree(x - i - 1, y) == undefined) return true;
        }
        for (let i = 1; this.getTree(x, y + i).height < tree.height; i++) {
            if (this.getTree(x, y + i + 1) == undefined) return true;
        }
        for (let i = 1; this.getTree(x, y - i).height < tree.height; i++) {
            if (this.getTree(x, y - i - 1) == undefined) return true;
        }
        return false;
    }

    getScenicScore(x, y) {
        let tree = this.getTree(x, y);
        if (tree == undefined) return 0;
        let visible = [0, 0, 0, 0];
        for (let curTree = tree.getNextTreeX(1); curTree != undefined; curTree = curTree.getNextTreeX(1)) {
            visible[0]++;
            if (curTree.height >= tree.height) break;
        }
        for (let curTree = tree.getNextTreeX(-1); curTree != undefined; curTree = curTree.getNextTreeX(-1)) {
            visible[1]++;
            if (curTree.height >= tree.height) break;
        }
        for (let curTree = tree.getNextTreeY(1); curTree != undefined; curTree = curTree.getNextTreeY(1)) {
            visible[2]++;
            if (curTree.height >= tree.height) break;
        }
        for (let curTree = tree.getNextTreeY(-1); curTree != undefined; curTree = curTree.getNextTreeY(-1)) {
            visible[3]++;
            if (curTree.height >= tree.height) break;
        }
        return visible.reduce((acc, elem) => acc *= (elem == 0 ? 1 : elem), 1);
    }

    isTreeBorder(x, y) {
        return this.getTree(x, y) != undefined && (this.getTree(x + 1, y) == undefined || this.getTree(x - 1, y) == undefined || this.getTree(x, y + 1) == undefined || this.getTree(x, y - 1) == undefined);
    }

    getAllTrees() {
        return this.trees.flat();
    }
}

function truncateArray(arr) {
    if (arr[0] == "") arr.shift();
    if (arr[arr.length - 1] == "") arr.pop();
    return arr;
}