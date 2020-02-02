class Box {
    public x: number;
    public y: number;
    public height: number;
    public width: number;

    constructor();
    constructor(obj: String);
    constructor(obj?: any) {
        asserts
        this.x = obj && obj.x || 0
        this.y = obj && obj.y || 0
        this.height = obj && obj.height || 0
        this.width = obj && obj.width || 0;
    }
}
