import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubTopicMasterDetailComponent } from 'app/entities/sub-topic-master/sub-topic-master-detail.component';
import { SubTopicMaster } from 'app/shared/model/sub-topic-master.model';

describe('Component Tests', () => {
  describe('SubTopicMaster Management Detail Component', () => {
    let comp: SubTopicMasterDetailComponent;
    let fixture: ComponentFixture<SubTopicMasterDetailComponent>;
    const route = ({ data: of({ subTopicMaster: new SubTopicMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubTopicMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SubTopicMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubTopicMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subTopicMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subTopicMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
