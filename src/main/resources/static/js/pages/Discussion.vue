<template>
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <slot v-for="(board, index) in getBoards">
            <v-flex :xs4="!$vuetify.breakpoint.mdAndDown" :xs12="$vuetify.breakpoint.mdAndDown">
                <v-card class="my-1 mx-1 px-2">
                    <v-card-title primary class="headline">
                        <span style="display:block; width: 70%; word-wrap:break-word;">{{ board.name }}</span>
                        <v-spacer></v-spacer>
                    <v-btn flat icon @click="() => showContent(index)">
                        <v-icon>arrow_drop_down</v-icon>
                    </v-btn>
                        <v-btn flat icon @click="() => addItem(index)">
                            <v-icon>add</v-icon>
                        </v-btn>
                    </v-card-title>
                    <slot v-if="newItem.board && newItem.board.id === openBoards[index].id">
                        <v-divider></v-divider>
                        <v-flex xs12>
                            <v-select
                                    :items="boardItems"
                                    :placeholder="$t('discussion.boardItem.typePlaceholder')"
                                    v-model="newItem.type">
                                <template slot="item" slot-scope="data">
                                    {{$t(`discussion.boardItem.types.${data.item.toLowerCase()}`)}}
                                </template>
                                <template slot="selection" slot-scope="data">
                                    {{$t(`discussion.boardItem.types.${data.item.toLowerCase()}`)}}
                                </template>
                            </v-select>
                        </v-flex>
                        <slot v-if="newItem.type === 'MESSAGE'">
                            <v-flex xs12>
                                <v-divider></v-divider>
                            </v-flex>
                            <v-flex xs12>
                                <v-textarea :placeholder="$t('discussion.boardItem.placeholders.message')"
                                            v-model="newItem.messageText">
                                </v-textarea>
                            </v-flex>
                            <v-flex xs12 text-xs-center>
                            <v-btn color="success" @click="saveItem">
                                <v-icon>save</v-icon>
                            </v-btn>
                            </v-flex>
                        </slot>
                        <slot v-else-if="newItem.type === 'IMAGE'">
                            <slot v-if="newImageItem.imageUrl">
                            <v-flex xs12>
                                <v-divider></v-divider>
                            </v-flex>
                            <v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center pt-1">
                                <img :src="newImageItem.imageUrl" width="100%"/>
                            </v-flex>
                            </slot>
                            <v-flex xs12>
                                <v-divider></v-divider>
                            </v-flex>
                            <v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
                                <v-text-field
                                        :placeholder="$t('discussion.boardItem.placeholders.image')"
                                        @click='pickImage'
                                        v-model='newImageItem.imageName'
                                        prepend-icon='attach_file'>
                                </v-text-field>
                                <input type="file"
                                       style="display: none"
                                       ref="image"
                                       accept="image/*"
                                       @change="onImagePicked">
                            </v-flex>
                            <v-flex xs12 text-xs-center>
                                <v-btn color="success" @click="saveItem">
                                    <v-icon>save</v-icon>
                                </v-btn>
                            </v-flex>
                        </slot>
                        <slot v-else-if="newItem.type === 'ATTACHMENT'">
                            <v-flex xs12>
                                <v-divider></v-divider>
                            </v-flex>
                            <v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
                                <v-text-field
                                        :placeholder="$t('discussion.boardItem.placeholders.attachment')"
                                        @click='pickFile'
                                        v-model='newFileItem.fileName'
                                        prepend-icon='attach_file'>
                                </v-text-field>
                                <input type="file"
                                       style="display: none"
                                       ref="file"
                                       @change="onFilePicked">
                            </v-flex>
                            <v-flex xs12 text-xs-center>
                                <v-btn color="success" @click="saveItem">
                                    <v-icon>save</v-icon>
                                </v-btn>
                            </v-flex>
                        </slot>
                    </slot>
                    <slot v-if="openBoards[index].isShown">
                        <slot v-if="board.items && board.items.length > 0">
                            <template v-for="item in board.items">
                            <v-card flat>
                                <v-card-actions><v-divider></v-divider></v-card-actions>
                                <span>
                                    <v-list-tile class="grow">
                                        <v-list-tile-avatar v-if="item.author">
                                        <v-img class="elevation-6"
                                               :src="item.author.userpic">
                                        </v-img>
                                        </v-list-tile-avatar>
                                        <v-list-tile-content>
                                            <v-list-tile-title>{{ item.author.name }}</v-list-tile-title>
                                        </v-list-tile-content>
                                    </v-list-tile>
                                </span>
                                <v-card-text>
                                <slot v-if="item.type === 'MESSAGE'">
                                    <span style="display: block; word-wrap: break-word">{{item.messageText}}</span>
                                </slot>
                                <slot v-else-if="item.type === 'IMAGE'">
                                    <v-img width="100%" height="100%"
                                           :src="`data:image/png;base64,${item.image}`">
                                    </v-img>
                                </slot>
                                    <slot v-else-if="item.type === 'ATTACHMENT'" >
                                        <div class="text-xs-center">
                                            <v-btn block outline color="indigo" @click="() =>
                                            saveFile(item.file, item.fileName, item.fileType)">
                                                <v-icon left>cloud_upload</v-icon>
                                                {{item.fileName}}</v-btn>
                                        </div>
                                    </slot>
                                </v-card-text>
                            </v-card>
                            </template>
                        </slot>
                        <slot v-else>
                        <v-flex xs12>
                            <v-divider></v-divider>
                            <v-card-text>
                                <span>{{$t('discussion.boardItem.emptyMessage')}}</span>
                            </v-card-text>
                        </v-flex>
                        </slot>
                    </slot>
                </v-card>
            </v-flex>
            </slot>
            <v-flex shrink :xs4="!$vuetify.breakpoint.mdAndDown" :xs12="$vuetify.breakpoint.mdAndDown" text-xs-center>
                <v-card class="my-1 mx-1">
                    <v-card-title primary class="headline">{{$t('discussion.boardItem.title')}}
                        <v-spacer></v-spacer>
                        <v-btn color="success" @click="addNewBoard">
                            <v-icon>add</v-icon>
                        </v-btn>
                    </v-card-title>
                    <v-card-text>
                        <v-text-field
                                :placeholder="$t('discussion.boardItem.placeholders.title')"
                                v-model="newBoard.name">
                        </v-text-field>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapActions, mapGetters } from 'vuex'
    import BoardItem from 'domain/BoardItem'
    import savingFileMixin from 'mixin/savingFileMixin'
    export default {
        mixins: [ savingFileMixin ],
        data() {
            return {
                newBoard: { name: '' },
                boards: [],
                openBoards: [],
                newItem: {},
                newImageItem: {
                    title: "Image Upload",
                    dialog: false,
                    imageName: '',
                    imageUrl: '',
                    imageFile: ''
                },
                newFileItem: {
                    title: "File Upload",
                    dialog: false,
                    fileName: '',
                    fileUrl: ''
                }
            }
        },
        computed: {
            ...mapGetters(['getBoards', 'boardItems'])
        },
        created () {
            this.getBoards.forEach(board => this.openBoards.push({id: board.id, isShown: false}))
        },
        watch: {
            getBoards(newValue, oldValue) {
                if (newValue.length > oldValue.length) {
                    const value = newValue[newValue.length - 1]
                    this.openBoards.push({id: value.id, isShown: false})
                } else if (newValue.length < oldValue.length) {
                }
            }
        },
        methods: {
            ...mapActions(['addBoardAction', 'addBoardItemAction']),
            addNewBoard() {
                if (!this.newBoard.name) { return; }
                this.addBoardAction(this.newBoard)
                this.newBoard.name = ''
            },
            showContent(index) {
                this.openBoards[index].isShown = !this.openBoards[index].isShown
            },
            addItem(index) {
                this.newItem = new BoardItem({}, this.openBoards[index].id)
            },
            saveItem() {
                this.addBoardItemAction(this.newItem)
                this.newItem = new BoardItem()
            },
            pickImage () {
                console.log(this.$refs)
                this.$refs.image[0].click()
            },
            onImagePicked (e) {
                const files = e.target.files
                if(files[0] !== undefined) {
                    this.newImageItem.imageName = files[0].name
                    if(this.newImageItem.imageName.lastIndexOf('.') <= 0) {
                        return
                    }
                    const fr = new FileReader ()
//                    fr.readAsDataURL(files[0])
//                    fr.addEventListener('load', () => {
//                        this.newImageItem.imageUrl = fr.result
//                        console.log(files[0])
//                        this.newImageItem.imageFile = files[0]// this is an image file that can be sent to server...
//                        this.newItem.image = fr.result
//                    })
                    fr.readAsArrayBuffer(files[0])
                    fr.addEventListener('load', () => {
                        let fileByteArray = []
//                        console.log(fr.result)
//                        console.log(new Uint8Array(fr.result))
                        let arrayBuffer = fr.result
                        let array = new Uint8Array(arrayBuffer)
                        for (let i = 0; i < array.length; i++) {
                            fileByteArray.push(array[i]);
                        }
//                        console.log(fileByteArray)
                        this.newItem.image = fileByteArray
                    })
                }
                else {
                    this.newImageItem.imageName = ''
                    this.newImageItem.imageFile = ''
                    this.newImageItem.imageUrl = ''
                }
            },
            pickFile () {
                console.log(this.$refs)
                this.$refs.file[0].click()
            },
            onFilePicked (e) {
                const files = e.target.files
                if(files[0] !== undefined) {
                    this.newFileItem.fileName = files[0].name
                    const fr = new FileReader ()
                    fr.readAsArrayBuffer(files[0])
                    fr.addEventListener('load', () => {
                        let fileByteArray = []
                        let arrayBuffer = fr.result
                        let array = new Uint8Array(arrayBuffer)
                        for (let i = 0; i < array.length; i++) {
                            fileByteArray.push(array[i]);
                        }
                        this.newItem.file = fileByteArray
                        this.newItem.fileName = this.newFileItem.fileName
                        this.newItem.fileType = files[0].type
                    })
                } else {
                    this.newFileItem.fileName = ''
                    this.newFileItem.fileUrl = ''
                }
            }
        }
    }
</script>