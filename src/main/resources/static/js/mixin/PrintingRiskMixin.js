import pdfMake from 'pdfMake/build/pdfmake.min'
import pdfFonts from 'pdfmake/build/vfs_fonts'
import JSZip from 'jszip'
import {i18n} from 'localization/i18n'
import Risk from 'domain/Risk'

export default {
    methods: {
        convertToPdf(risk) {
            let content = [
                {
                    text: `${i18n.tc('risk.fields.id')}: ${risk.id}`,
                    style: 'header'
                },
                {
                    text: `${i18n.tc('risk.fields.title')}: ${risk.text}`,
                    style: 'default'
                }
            ]

            // Don't need 'id' and 'text' cause these properties are explicitly added to content array
            // Property/field 'Responsible users' needs manual parsing
            // Comments section is not included in .pdf file
            // Notifications flag is not included in .pdf file
            // Stage is not included. This field is only for UI form.
            const keys = Object.keys(risk).filter(key =>
                (String)(key) !== 'id' &&
                (String)(key) !== 'text' &&
                (String)(key) !== 'comments' &&
                (String)(key) !== 'responsible' &&
                (String)(key) !== 'areNotificationSent' &&
                (String)(key) !== 'stage'
            )

            keys.forEach(function (key) {
                if (risk[key] !== null && risk[key] !== undefined && risk[key] !== '') {
                    let text = String(key).replace( /([A-Z])/g, " $1" );
                    let sentenceCaseText = text.charAt(0).toUpperCase() + text.slice(1);
                    let value = Risk.convertEnumToValue(key, risk[key]) || risk[key]
                    let textLabel = i18n.tc(`risk.fields.${key}`) || sentenceCaseText
                    content.push ({
                        text: `${textLabel}: ${value}`,
                        style: 'default'
                    })
                }
            })

            let responsibleUsers = risk['responsible']

            if(responsibleUsers !== null && responsibleUsers !== undefined && responsibleUsers.length > 0) {
                let usersEmails = []
                responsibleUsers.forEach(user => usersEmails.push(user.email))
                content.push({
                    text: `${i18n.tc('risk.fields.responsible')}: ${usersEmails.join(', ')}`,
                    style: 'default'
                })
            }

            return {
                content,
                styles: {
                    header: {
                        fontSize: 18,
                        bold: true
                    },
                    default: {
                        fontSize: 14
                    }
                }
            }
        },
        savePDF(risk) {
            pdfMake.vfs = pdfFonts.pdfMake.vfs

            pdfMake.createPdf(this.convertToPdf(risk)).download(`Risk${risk.id}`)
        },
        async saveAllPDF(risks, projectId) {
            pdfMake.vfs = pdfFonts.pdfMake.vfs

            let zip = new JSZip()
            let folder = zip.folder(`Project${projectId}`);
            let this$ = this

            let event = document.createEvent('Event');
            event.initEvent('zipIsReady', true, true);

            document.addEventListener('zipIsReady', function (e) {
                zip.generateAsync({type: "blob"})
                        .then(async function (content) {
                            await saveAs(content, `Project${projectId}`);
                        });
            }, false);

            risks.forEach(function (risk, riskIndex) {
                let pdfBlob = null
                let pdfFile = this$.convertToPdf(risk)
                const pdfDocGenerator = pdfMake.createPdf(pdfFile)

                pdfDocGenerator.getBlob((blob) => {
                    pdfBlob = blob
                    folder.file(`Risk${risk.id}.pdf`, pdfBlob, {binary: true, compression: "DEFLATE"})
                    if (risks.length - 1 === riskIndex) {
                        document.dispatchEvent(event);
                    }
                })
            })
        }
    }
}