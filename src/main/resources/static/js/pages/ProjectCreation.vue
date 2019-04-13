<template>
    <v-container v-if="validation" grid-list-md text-xs-center>
            <v-card class="py-2 px-2">
                    <v-card-title>
                        <v-list-tile-avatar color="grey darken-3">
                            <v-img class="elevation-6"
                                   :src="getProfile.userpic">
                            </v-img>
                        </v-list-tile-avatar>
                        <span class="headline font-weight-light">{{ newProject.name || 'New project' }}</span>
                    </v-card-title>

                <v-form ref="form" lazy-validation>
                    <v-layout row wrap align-center>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Project name</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-text-field placeholder="Name"
                                          v-model="newProject.name"
                                          :rules="validation.name"
                                          required>
                            </v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Project description</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-textarea placeholder="Description"
                                        v-model="newProject.description"
                                        :rules="validation.description"
                                        required>
                            </v-textarea>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Managers</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.managers"
                                      :items="users"
                                      placeholder="Managers"
                                      :rules="validation.managers"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Analytics</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.analytics"
                                      :items="users"
                                      placeholder="Analytics"
                                      :rules="validation.analytics"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Developers</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.developers"
                                      :items="users"
                                      placeholder="Developers"
                                      :rules="validation.developers"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Testers</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.testers"
                                      :items="users"
                                      placeholder="Testers"
                                      :rules="validation.testers"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Others</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.others"
                                      :items="users"
                                      placeholder="Others"
                                      :rules="validation.others"
                                      required
                                      multiple>
                            </v-select>
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
    import validation from 'validation/ProjectFormValidation'

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
            users() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            }
        },
        created () {
            this.validation = validation
            this.newProject = new Project()
        },
        methods: {
            ...mapActions(['addProjectAction', 'updateProjectAction', 'removeProjectAction']),
            async save() {
                if (!this.$refs.form.validate()) { return; }
                this.newProject.managers = this.newProject.managers.map(x => this.getUserByEmail(x))
                this.newProject.analytics = this.newProject.analytics.map(x => this.getUserByEmail(x))
                this.newProject.developers = this.newProject.developers.map(x => this.getUserByEmail(x))
                this.newProject.testers = this.newProject.testers.map(x => this.getUserByEmail(x))
                this.newProject.others = this.newProject.others.map(x => this.getUserByEmail(x))

                this.newProject["id"] = this.id
                await this.addProjectAction(this.newProject)

                this.$router.push({ name: 'ProjectsList' })
            }
        }
    }
</script>

<style scoped>

</style>