import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { UserDataService } from '../service/user_data.service';



@Component({
  selector: 'app-datatable',
  templateUrl: './datatable.component.html',
  styleUrls: ['./datatable.component.css']
})


export class DatatableComponent implements OnInit ,OnDestroy,AfterViewInit{

  @ViewChild(DataTableDirective, {static: false})  datatableElement!: DataTableDirective;

  searchForm !:  FormGroup;

  userDataList : any[] =[];

  dataTableErrorMsg : boolean = false;

  dtOptions: DataTables.Settings ={};

  dtTrigger: Subject<any> = new Subject();

  req : any = {}

  constructor(private userDataService : UserDataService,private fb : FormBuilder) {}

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      'fullName' :[null],
      'emailId' :[null],
      'country' :[null]

    });

    this.req = {
      'searchType' : 'global'
    }
    this.dtOptions = this.getDtOptions();

  }


  ngAfterViewInit(): void {
    
    this.dtTrigger.next(false);

  } 

  searchData(){

    let req = {
      'searchType' :'Fillter',
      'fullName' :this.searchForm.get('fullName')?.value,
      'emailId' :this.searchForm.get('emailId')?.value,
      'country' :this.searchForm.get('country')?.value
    }
    

    this.datatableElement.dtInstance.then((dtInstance : DataTables.Api) =>{
      dtInstance.destroy();
      this.req = req;      
      this.dtTrigger.next(false);
    });

    this.dataTableErrorMsg = false;

  }

  clearData(){


    let req = {
      'searchType' :'global',
    }

    this.datatableElement.dtInstance.then((dtInstance : DataTables.Api) =>{
      dtInstance.destroy();     
      this.req = req;    
      this.dtTrigger.next(false);
    });

    this.dataTableErrorMsg = false;
    this.searchForm.reset();

  }

  

  getDtOptions(){

   

    
         
    this.dtOptions ={
      pagingType : 'simple_numbers',
      pageLength :5,
      serverSide : true,
      processing : true,
     
      ajax :(dataTableParameters : any , callback) =>{
        console.log(dataTableParameters);
        Object.assign(dataTableParameters,this.req);
        this.userDataService.getUserData(dataTableParameters).subscribe(res => {

          this.userDataList = res.responseList.data;

          if(null != this.userDataList && this.userDataList.length > 0){
            this.dataTableErrorMsg = true;
          }else{
            this.dataTableErrorMsg = false;
          }
         
          callback({
            recordsTotal : res.responseList.recordsTotal,
            recordsFiltered: res.responseList.recordsFiltered,
            data: []
          });
        });
      },
      columns :[{data : 'id',title:'id'},{data :'fullName',title:'fullName'},{data :'phone',title:'phone'},{data :'emailId',title:'emailId'},{data :'postalZip',title:'postalZip'},
      {data :'region',title:'region'},{data :'country',title:'country'}]
    }

    return this.dtOptions;
    

  }


  ngOnDestroy(): void {
      this.dtTrigger.unsubscribe();
  }


}
