export default class BoardItem {
    constructor (item, boardId) {
        if (item) {
            this.id = item.id
            this.creationDate = item.creationDate
            this.messageText = item.messageText
            this.image = item.image
            this.file = item.file
            this.fileName = item.fileName
            this.fileType = item.fileType
            this.type = item.type
        } else {
            this.id = ''
            this.creationDate = null
            this.messageText = ''
            this.image = null
            this.type = null
            this.file = null
            this.fileName = ''
            this.fileType = ''
        }

        if (boardId) {
            this.board = { id: boardId }
        }
    }
}