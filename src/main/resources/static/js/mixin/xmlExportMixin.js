import xml2js from 'xml2js'
import { saveAs } from 'file-saver';
import JSZip from 'jszip'

export default {
    methods: {
        saveXML(risk) {

            let builder = new xml2js.Builder();
            let xml = builder.buildObject(risk);

            let blob = new Blob([xml], {type: "text/xml"});
            saveAs(blob, `Risk${risk.id}`)
        },
        saveAllXML(risks, projectId) {
            let zip = new JSZip()
            let folder = zip.folder(`Project${projectId}`);

            risks.forEach(function (risk) {
                let builder = new xml2js.Builder();
                let xml = builder.buildObject(risk);

                let blob = new Blob([xml], {type: "text/xml"});
                console.log(blob)

                folder.file(`Risk${risk.id}`, blob)
            })

            zip.generateAsync({type:"blob"})
                .then(function(content) {
                    saveAs(content, `Project${projectId}`);
                });
        }
    }
}