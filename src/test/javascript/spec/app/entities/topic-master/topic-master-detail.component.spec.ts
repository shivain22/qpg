import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { TopicMasterDetailComponent } from 'app/entities/topic-master/topic-master-detail.component';
import { TopicMaster } from 'app/shared/model/topic-master.model';

describe('Component Tests', () => {
  describe('TopicMaster Management Detail Component', () => {
    let comp: TopicMasterDetailComponent;
    let fixture: ComponentFixture<TopicMasterDetailComponent>;
    const route = ({ data: of({ topicMaster: new TopicMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [TopicMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
