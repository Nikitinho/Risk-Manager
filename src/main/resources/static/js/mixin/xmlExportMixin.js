import xml2js from 'xml2js'
import { saveAs } from 'file-saver';
import JSZip from 'jszip'

export default {
    methods: {
        convertToXML(risk) {
            let builder = new xml2js.Builder();
            let xml = builder.buildObject(risk);

            return new Blob([xml], {type: "text/xml"});
        },
        saveXML(risk) {
            saveAs(this.convertToXML(risk), `Risk${risk.id}`)
        },
        saveAllXML(risks, projectId) {
            let zip = new JSZip()
            let folder = zip.folder(`Project${projectId}`);
            let this$ = this

            risks.forEach(function (risk) {
                let blob = this$.convertToXML(risk)
                folder.file(`Risk${risk.id}`, blob)
            })

            zip.generateAsync({type:"blob"})
                .then(function(content) {
                    saveAs(content, `Project${projectId}`);
                });
        }
    }
}