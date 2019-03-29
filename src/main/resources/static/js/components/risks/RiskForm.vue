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
    import { sendRisk } from 'util/ws'

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
                sendRisk({id: this.id, text: this.text})
                this.text = ''
                this.id = ''

                /*const risk = { text: this.text };

                if (this.id) {
                    this.$resource('/risk{/id}').update({id: this.id}, risk).then(result =>
                        result.json().then(data => {
                            const index = getIndex(this.risks, data.id);
                            this.risks.splice(index, 1, data)
                            this.text = ''
                            this.id = ''
                        })
                    )
                } else {
                    this.$resource('/risk{/id}').save({}, risk).then(result =>
                        result.json().then(data => {
                            this.risks.push(data);
                            this.text = ''
                        })
                    )
                }*/
            }
        }
    }
</script>

<style>

</style>