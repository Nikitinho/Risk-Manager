import { saveAs } from 'file-saver';

export default {
    methods: {
        saveFile(bytes) {

            let blob = new Blob([bytes]);
            saveAs(blob, `newFile`)
        }
    }
}