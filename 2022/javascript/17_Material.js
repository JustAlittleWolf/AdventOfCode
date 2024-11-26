export var Material;
(function (Material) {
    Material["Air"] = ".";
    Material["Rock"] = "#";
})(Material || (Material = {}));
export function fromString(input) {
    switch (input) {
        case Material.Air:
            return Material.Air;
        case Material.Rock:
            return Material.Rock;
        default:
            return Material.Air;
    }
}
