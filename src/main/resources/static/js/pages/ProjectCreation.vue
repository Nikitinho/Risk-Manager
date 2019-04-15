<template>
    <v-container v-if="validation" grid-list-md text-xs-center>
            <v-card class="py-2 px-2">
                    <v-card-title>
                        <v-list-tile-avatar color="grey darken-3">
                            <v-img class="elevation-6"
                                   :src="getProfile.userpic">
                            </v-img>
                        </v-list-tile-avatar>
                        <span class="headline font-weight-light">{{ newProject.name || $t('project.fields.name') }}</span>
                    </v-card-title>

                <v-form ref="form" lazy-validation>
                    <v-layout row wrap align-center>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.name')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-text-field :placeholder="$t('project.fields.name')"
                                          v-model="newProject.name"
                                          :rules="validation.name"
                                          required>
                            </v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.description')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-textarea :placeholder="$t('project.fields.description')"
                                        v-model="newProject.description"
                                        :rules="validation.description"
                                        required>
                            </v-textarea>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.type')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select :items="getProjectTypes"
                                      :placeholder="$t('project.fields.type')"
                                      v-model="newProject.type"
                                      :rules="validation.type"
                                      required>
                                <template slot="item" slot-scope="data">
                                    {{$t(`project.types.${data.item.toLowerCase()}`)}}
                                </template>
                                <template slot="selection" slot-scope="data">
                                    {{$t(`project.types.${data.item.toLowerCase()}`)}}
                                </template>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.managers')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.managers"
                                      :items="users"
                                      :placeholder="$t('project.fields.managers')"
                                      :rules="validation.managers"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.analytics')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.analytics"
                                      :items="users"
                                      :placeholder="$t('project.fields.analytics')"
                                      :rules="validation.analytics"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.developers')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.developers"
                                      :items="users"
                                      :placeholder="$t('project.fields.developers')"
                                      :rules="validation.developers"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.testers')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.testers"
                                      :items="users"
                                      :placeholder="$t('project.fields.testers')"
                                      :rules="validation.testers"
                                      required
                                      multiple>
                            </v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>{{$t('project.fields.others')}}</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-select chips
                                      v-model="newProject.others"
                                      :items="users"
                                      :placeholder="$t('project.fields.others')"
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

    export default {
        name: 'ProjectCreation',
        props: ['projectId'],
        data() {
            return {
                newProject: null
            }
        },
        computed: {
            ...mapGetters(['activeUsers', 'getUserByEmail', 'getProjectById', 'getProfile', 'getProjectTypes']),
            users() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            }
        },
        created () {
            this.validation = require(`validation/${this.$i18n.locale}/ProjectFormValidation`).default
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