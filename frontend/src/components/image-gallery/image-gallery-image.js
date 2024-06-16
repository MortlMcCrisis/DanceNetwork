import Card from "../Card";
import {Link} from "react-router-dom";
import React from "react";

const DanceImageGalleryImage=({selectedIndex, index, setSelectedIndex, item})=> {

  const handleCardClick = () => {
    setSelectedIndex(index);
  };

  return(
      <div onClick={handleCardClick}>
        <Card
            className={`${selectedIndex === index ? "image-gallery-selected" : "image-gallery-hoverable"}`}>
          <Card.Body>
            <div className="event-images position-relative">
              <Link to="#">
                <img loading="lazy" src={item.path} className="img-fluid" alt="Responsive" />
              </Link>
            </div>
            <hr className="hr-horizontal"/>
            <h4>
              {item.title}
            </h4>
          </Card.Body>
        </Card>
      </div>
  )
}

export default DanceImageGalleryImage