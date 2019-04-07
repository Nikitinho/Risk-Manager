import xml2js from 'xml2js'
import { saveAs } from 'file-saver';

export default {
    methods: {
        saveXML(risk) {

            let builder = new xml2js.Builder();
            let xml = builder.buildObject(risk);

            let blob = new Blob([xml], {type: "text/xml"});
            saveAs(blob, `Risk${risk.id}`)
            console.log(xml)
        }
    }
}