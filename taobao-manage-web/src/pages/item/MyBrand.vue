<template>
  <div>
    <v-layout>
      <v-flex xs2><v-btn color="info">新增</v-btn></v-flex>
      <v-spacer></v-spacer>
      <v-flex xs4><v-text-field append-icon="search" v-model="search" label="输入关键字搜索"  hide-details></v-text-field></v-flex>
    </v-layout>


    <v-data-table
      :headers="headers"
      :items="brands"
      :search="search"
      :pagination.sync="pagination"
      :total-items="totalBrands"
      :loading="loading"
      class="elevation-1"
    >
      <template  slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.id }}</td>
        <td class="text-xs-center">{{ props.item.name }}</td>
        <td class="text-xs-center"><img :src="props.item.image"/></td>
        <td class="text-xs-center">{{ props.item.letter }}</td>
        <td class="text-xs-center">
          <v-btn flat icon color="info">
            <v-icon>edit</v-icon>
          </v-btn>
          <v-btn flat icon color="error">
            <v-icon>delete</v-icon>
          </v-btn>
        </td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
  export default {
    name: "MyBrrand",
    data(){
      return {
        headers: [
          {text: '品牌ID',align: 'center',sortable: false,value: 'id'},
          { text: '品牌名称', align:"center",sortable:true,value: 'name' },
          { text: '图片', align:"center",sortable:false, value: 'image' },
          { text: '品牌首字', align:"center",sortable:true, value: 'letter'},
          { text: '操作', align:"center"}
        ],
        brands:[],
        search: '',
        pagination: {},
        totalBrands:0,
        loading:false
      };
    },
    created(){
      // this.brands = [
      //   {id: '1001',name: '华为',image: '1.jpg',letter: 'H',},
      //   {id: '1002',name: '小米',image: '2.jpg',letter: 'X',},
      //   {id: '1003',name: '京东',image: '3.jpg',letter: 'J',},
      //   {id: '1004',name: '淘宝',image: '4.jpg',letter: 'T',},
      //   {id: '1005',name: '淘宝',image: '4.jpg',letter: 'T',}
      // ];
      // this.totalBrands = 15;
      this.getBrandsByPagination();
    },
    watch:{
      search: { // 监视搜索字段
        handler() {
          this.getBrandsByPagination();
        }
      },
      pagination:{
        deep:true,
        handler() {
          this.getBrandsByPagination();
        }
      }
    },
    methods:{
      getBrandsByPagination(){
//发送ajax请求
        this.$http.get("/item/brand/list",
          {
            params:{
              page:this.pagination.page,
              rowsPerPage:this.pagination.rowsPerPage,
              sortBy: this.pagination.sortBy,
              search: this.search,
              desc: this.pagination.descending
            }
          })
          .then(
            response=>{
              // console.log(response);
              console.log(response.data.items);
              console.log(response.data.total);
              console.log(this.brands);
              console.log(this);
              this.brands = response.data.items;
              this.totalBrands = response.data.total;
            });
      }
    }
  }

</script>

<style scoped>

</style>
