import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";


class DataTablesResponse {
    data!: any[];
    draw!: number;
    recordsFiltered!: number;
    recordsTotal!: number;
  }

@Injectable({
    providedIn:'root'
})
export class UserDataService {

    constructor(private http : HttpClient){}

    baseUrl = environment.baseUrl;

    getUserData(req : any) : Observable<any>{
        return this.http.post<DataTablesResponse>(this.baseUrl+'/getAllUserData',req);
    }

    searchData(req : any) : Observable<any>{
        return this.http.post(this.baseUrl + "/searchData",req);
    }
}