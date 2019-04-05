<template>
    <v-container grid-list-md text-xs-center>
            <v-card class="py-2 px-2">
                <v-form lazy-validation>
                    <v-layout row wrap align-center>
                        <v-flex xs12>
                            <v-text-field placeholder="Name"
                                          v-model="newProject.name">
                            </v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-text-field placeholder="Description"
                                          v-model="newProject.description">
                            </v-text-field>
                        </v-flex>
                        <v-flex xs12 slot="footer-buttons">
                            <v-btn color="success"
                                   @click="save">
                                Save
                            </v-btn>
                        </v-flex>
                    </v-layout>
                </v-form>
            </v-card>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'
    import Project from 'domain/Project'

    export default {
        name: 'ProjectCreation',
        props: ['projectId'],
        data() {
            return {
                newProject: null
            }
        },
        computed: {
            ...mapGetters(['activeUsers', 'getUserByEmail', 'getProjectById', 'getProfile']),
            responsibleUsers() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            }
        },
        created () {
            this.newProject = new Project()
        },
        methods: {
            ...mapActions(['addProjectAction', 'updateProjectAction', 'removeProjectAction']),
            getResponsibleNames(people) {
                let users = []
                Array.from(people).forEach(user =>
                    users.push(user.email)
                )
                return users
            },
            save() {
                this.newProject.responsible = this.newProject.responsible.map(x => this.getUserByEmail(x))

                this.newProject["id"] = this.id
                this.addProjectAction(this.newProject)

                this.$router.push({ name: 'ProjectsList' })
            }
        }
    }
</script>

<style scoped>

</style>