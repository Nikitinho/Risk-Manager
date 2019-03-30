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
    import { mapActions } from 'vuex'

    export default {
        props: ['riskAttr'],
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
            ...mapActions(['addRiskAction', 'updateRiskAction']),
            save() {
                const risk = {
                    id: this.id,
                    text: this.text
                };

                if (this.id) {
                    this.updateRiskAction(risk)
                } else {
                    this.addRiskAction(risk)
                }
                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style>

</style>