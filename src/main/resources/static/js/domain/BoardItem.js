export default class BoardItem {
    constructor (item, boardId) {
        if (item) {
            this.id = item.id
            this.creationDate = item.creationDate
            this.messageText = item.messageText
            this.type = BoardItem.convertBoardItem(item.type)
        } else {
            this.id = ''
            this.creationDate = null
            this.messageText = ''
            this.type = null
        }

        if (boardId) {
            this.board = { id: boardId }
        }
    }

    static convertBoardItem(item) {
        switch (item) {
            case 'MESSAGE':
                return 'Текстовое сообщение'
            case 'POLL':
                return 'Опрос'
            case 'IMAGE':
                return 'Изображение'
            case 'ATTACHMENT':
                return 'Вложение'
            default:
                return null
        }
    }
}