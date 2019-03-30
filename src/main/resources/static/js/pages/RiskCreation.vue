<template>
    <v-container grid-list-md text-xs-center>
        <v-layout row wrap>
            <v-flex xs12>
                <v-text-field
                        label="Title"
                        v-model="text">
                </v-text-field>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Description"
                        v-model="description">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Causes of"
                        v-model="causes">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Description of the consequences"
                        v-model="consequences">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-text-field
                        label="Responsible people"
                        v-model="responsible">
                </v-text-field>
            </v-flex>
            <v-flex xs12>
                <v-btn @click="save">
                    Save
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        name: 'RiskCreation',
        data() {
            return {
                text: '',
                description: '',
                causes: '',
                consequences: '',
                responsible: ''
            }
        },
        watch: {
            riskAttr (newVal, oldVal) {
                this.text = newVal.text
                this.description = newVal.description
                this.causes = newVal.causes
                this.consequences = newVal.consequences
                this.responsible = newVal.responsible
            }
        },
        methods: {
            ...mapActions(['addRiskAction']),
            save() {
                const risk = {
                    id: this.id,
                    text: this.text,
                    description: this.description
//                    causes: this.causes,
//                    consequences: this.consequences,
//                    responsible: this.responsible
                };

                this.addRiskAction(risk)

                this.text = ''
                this.id = ''
                this.creationDate = null
                this.description = ''
                this.causes = ''
                this.consequences = ''
                this.responsible = ''
            }
        }
        /*long valueFromClient = ...
            Date date = new Date(valueFromClient);
            String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            */
    }
</script>

<style scoped>

</style>