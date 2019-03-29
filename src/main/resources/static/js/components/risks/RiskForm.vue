<template>
    <v-layout row>
        <v-text-field
                label="New risk"
                placeholder="Write something" v-model="text"/>
        <v-btn @click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    import risksApi from 'api/risks'

    export default {
        props: ['risks', 'riskAttr'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            riskAttr (newVal, oldVal) {
                this.text = newVal.text;
                this.id = newVal.id;
            }
        },
        methods: {
            save() {
                const risk = {
                    id: this.id,
                    text: this.text
                };

                if (this.id) {
                    risksApi.update(risk).then(result =>
                        result.json().then(data => {
                            const index = this.risks.findIndex(item => item.id === data.id)
                            this.risks.splice(index, 1, data)
                        })
                    )
                } else {
                    risksApi.add(risk).then(result =>
                        result.json().then(data => {
                            const index = this.risks.findIndex(item => item.id === data.id)
                            if (index > -1) {
                                this.risks.splice(index, 1, data)
                            } else {
                                this.risks.push(data);
                            }
                        })
                    )
                }
                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style>

</style>