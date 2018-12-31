import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http'
import { HelperServiceService } from '../Services/helper-service.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-bookmark',
  templateUrl: './bookmark.component.html',
  styleUrls: ['./bookmark.component.css']
})
export class BookmarkComponent implements OnInit {

  baseUrl = `http://ec2-54-210-42-186.compute-1.amazonaws.com:8080/Pipeline/bookmark/add`;
  bookUrl = '';
  bookmark: any;
  result:any;

  @Input() url: string;
  @Input() name: string;
  constructor(private http: HttpClient, private helpMe: HelperServiceService) { }

  ngOnInit() {
  }



  bookmarkUrl(){
    let re = / /gi;
    let str = this.name;
    let urlName = str.replace(re, "%20");
    this.bookUrl = `${this.baseUrl}?name=${urlName}&url=${this.url}`
    this.bookmark = {
      user_id: this.helpMe.getSession().user_id,
      candidate: this.name,
      url: this.url
    }
    console.log(this.bookUrl);
    console.log(this.http.post<number>(this.bookUrl, this.bookmark));
    this.bookmarkService().subscribe(result => {
      this.result = result;
      console.log(this.result)
    })
    
  }
  bookmarkService(){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    }
    return  this.http.post<number>(this.bookUrl, this.bookmark, httpOptions).pipe(map(data => data));
  }
}
