<template>
    <v-container grid-list-md text-xs-center>
            <v-card class="py-2 px-2">


                    <v-card-title>
                        <v-list-tile-avatar color="grey darken-3">
                            <v-img class="elevation-6"
                                   :src="getProfile.userpic">
                            </v-img>
                        </v-list-tile-avatar>
                        <span class="headline font-weight-light">{{ newProject.name || 'New project' }}</span>
                    </v-card-title>

                <v-form lazy-validation>
                    <v-layout row wrap align-center>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Project name</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-text-field placeholder="Name"
                                          v-model="newProject.name">
                            </v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Project description</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-text-field placeholder="Description"
                                          v-model="newProject.description">
                            </v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Responsible people</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.responsible"
                                      :items="responsibleUsers"
                                      placeholder="Responsible people"
                                      multiple
                            ></v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
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
            async save() {
                this.newProject.responsible = this.newProject.responsible.map(x => this.getUserByEmail(x))

                this.newProject["id"] = this.id
                await this.addProjectAction(this.newProject)

                this.$router.push({ name: 'ProjectsList' })
            }
        }
    }
</script>

<style scoped>

</style>